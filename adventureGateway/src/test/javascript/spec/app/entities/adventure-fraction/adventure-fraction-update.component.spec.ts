/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureFractionUpdateComponent } from 'app/entities/adventure-fraction/adventure-fraction-update.component';
import { AdventureFractionService } from 'app/entities/adventure-fraction/adventure-fraction.service';
import { AdventureFraction } from 'app/shared/model/adventure-fraction.model';

describe('Component Tests', () => {
    describe('AdventureFraction Management Update Component', () => {
        let comp: AdventureFractionUpdateComponent;
        let fixture: ComponentFixture<AdventureFractionUpdateComponent>;
        let service: AdventureFractionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureFractionUpdateComponent]
            })
                .overrideTemplate(AdventureFractionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AdventureFractionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdventureFractionService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AdventureFraction(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.adventureFraction = entity;
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
                    const entity = new AdventureFraction();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.adventureFraction = entity;
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
