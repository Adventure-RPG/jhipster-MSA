/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureRaceOptionsDetailComponent } from 'app/entities/adventure-race-options/adventure-race-options-detail.component';
import { AdventureRaceOptions } from 'app/shared/model/adventure-race-options.model';

describe('Component Tests', () => {
    describe('AdventureRaceOptions Management Detail Component', () => {
        let comp: AdventureRaceOptionsDetailComponent;
        let fixture: ComponentFixture<AdventureRaceOptionsDetailComponent>;
        const route = ({ data: of({ adventureRaceOptions: new AdventureRaceOptions(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureRaceOptionsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AdventureRaceOptionsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdventureRaceOptionsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.adventureRaceOptions).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
