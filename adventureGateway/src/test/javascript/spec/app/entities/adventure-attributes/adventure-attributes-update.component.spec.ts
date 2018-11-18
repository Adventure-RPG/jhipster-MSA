/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureAttributesUpdateComponent } from 'app/entities/adventure-attributes/adventure-attributes-update.component';
import { AdventureAttributesService } from 'app/entities/adventure-attributes/adventure-attributes.service';
import { AdventureAttributes } from 'app/shared/model/adventure-attributes.model';

describe('Component Tests', () => {
    describe('AdventureAttributes Management Update Component', () => {
        let comp: AdventureAttributesUpdateComponent;
        let fixture: ComponentFixture<AdventureAttributesUpdateComponent>;
        let service: AdventureAttributesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureAttributesUpdateComponent]
            })
                .overrideTemplate(AdventureAttributesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AdventureAttributesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdventureAttributesService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AdventureAttributes(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.adventureAttributes = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AdventureAttributes();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.adventureAttributes = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
