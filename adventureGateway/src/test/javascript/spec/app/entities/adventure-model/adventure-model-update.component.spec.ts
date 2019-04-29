/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureModelUpdateComponent } from 'app/entities/adventure-model/adventure-model-update.component';
import { AdventureModelService } from 'app/entities/adventure-model/adventure-model.service';
import { AdventureModel } from 'app/shared/model/adventure-model.model';

describe('Component Tests', () => {
    describe('AdventureModel Management Update Component', () => {
        let comp: AdventureModelUpdateComponent;
        let fixture: ComponentFixture<AdventureModelUpdateComponent>;
        let service: AdventureModelService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureModelUpdateComponent]
            })
                .overrideTemplate(AdventureModelUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AdventureModelUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdventureModelService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AdventureModel('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.adventureModel = entity;
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
                    const entity = new AdventureModel();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.adventureModel = entity;
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
