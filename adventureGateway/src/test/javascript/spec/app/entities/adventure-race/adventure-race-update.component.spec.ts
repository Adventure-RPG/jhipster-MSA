/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureRaceUpdateComponent } from 'app/entities/adventure-race/adventure-race-update.component';
import { AdventureRaceService } from 'app/entities/adventure-race/adventure-race.service';
import { AdventureRace } from 'app/shared/model/adventure-race.model';

describe('Component Tests', () => {
    describe('AdventureRace Management Update Component', () => {
        let comp: AdventureRaceUpdateComponent;
        let fixture: ComponentFixture<AdventureRaceUpdateComponent>;
        let service: AdventureRaceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureRaceUpdateComponent]
            })
                .overrideTemplate(AdventureRaceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AdventureRaceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdventureRaceService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AdventureRace(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.adventureRace = entity;
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
                    const entity = new AdventureRace();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.adventureRace = entity;
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
