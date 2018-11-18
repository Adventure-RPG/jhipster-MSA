/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureCategoryTypeUpdateComponent } from 'app/entities/adventure-category-type/adventure-category-type-update.component';
import { AdventureCategoryTypeService } from 'app/entities/adventure-category-type/adventure-category-type.service';
import { AdventureCategoryType } from 'app/shared/model/adventure-category-type.model';

describe('Component Tests', () => {
    describe('AdventureCategoryType Management Update Component', () => {
        let comp: AdventureCategoryTypeUpdateComponent;
        let fixture: ComponentFixture<AdventureCategoryTypeUpdateComponent>;
        let service: AdventureCategoryTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureCategoryTypeUpdateComponent]
            })
                .overrideTemplate(AdventureCategoryTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AdventureCategoryTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdventureCategoryTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AdventureCategoryType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.adventureCategoryType = entity;
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
                    const entity = new AdventureCategoryType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.adventureCategoryType = entity;
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
