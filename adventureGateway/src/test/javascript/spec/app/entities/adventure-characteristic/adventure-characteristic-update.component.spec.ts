/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureCharacteristicUpdateComponent } from 'app/entities/adventure-characteristic/adventure-characteristic-update.component';
import { AdventureCharacteristicService } from 'app/entities/adventure-characteristic/adventure-characteristic.service';
import { AdventureCharacteristic } from 'app/shared/model/adventure-characteristic.model';

describe('Component Tests', () => {
    describe('AdventureCharacteristic Management Update Component', () => {
        let comp: AdventureCharacteristicUpdateComponent;
        let fixture: ComponentFixture<AdventureCharacteristicUpdateComponent>;
        let service: AdventureCharacteristicService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureCharacteristicUpdateComponent]
            })
                .overrideTemplate(AdventureCharacteristicUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AdventureCharacteristicUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdventureCharacteristicService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AdventureCharacteristic('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.adventureCharacteristic = entity;
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
                    const entity = new AdventureCharacteristic();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.adventureCharacteristic = entity;
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
