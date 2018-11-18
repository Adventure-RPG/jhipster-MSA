/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureRaceDetailComponent } from 'app/entities/adventure-race/adventure-race-detail.component';
import { AdventureRace } from 'app/shared/model/adventure-race.model';

describe('Component Tests', () => {
    describe('AdventureRace Management Detail Component', () => {
        let comp: AdventureRaceDetailComponent;
        let fixture: ComponentFixture<AdventureRaceDetailComponent>;
        const route = ({ data: of({ adventureRace: new AdventureRace(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureRaceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AdventureRaceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdventureRaceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.adventureRace).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
