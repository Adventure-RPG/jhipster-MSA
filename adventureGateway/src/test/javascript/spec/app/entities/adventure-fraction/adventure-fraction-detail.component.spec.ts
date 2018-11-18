/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureFractionDetailComponent } from 'app/entities/adventure-fraction/adventure-fraction-detail.component';
import { AdventureFraction } from 'app/shared/model/adventure-fraction.model';

describe('Component Tests', () => {
    describe('AdventureFraction Management Detail Component', () => {
        let comp: AdventureFractionDetailComponent;
        let fixture: ComponentFixture<AdventureFractionDetailComponent>;
        const route = ({ data: of({ adventureFraction: new AdventureFraction(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureFractionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AdventureFractionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdventureFractionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.adventureFraction).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
