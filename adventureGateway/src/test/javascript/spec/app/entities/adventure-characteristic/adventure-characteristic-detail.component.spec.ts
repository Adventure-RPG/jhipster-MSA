/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureCharacteristicDetailComponent } from 'app/entities/adventure-characteristic/adventure-characteristic-detail.component';
import { AdventureCharacteristic } from 'app/shared/model/adventure-characteristic.model';

describe('Component Tests', () => {
    describe('AdventureCharacteristic Management Detail Component', () => {
        let comp: AdventureCharacteristicDetailComponent;
        let fixture: ComponentFixture<AdventureCharacteristicDetailComponent>;
        const route = ({ data: of({ adventureCharacteristic: new AdventureCharacteristic(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureCharacteristicDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AdventureCharacteristicDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdventureCharacteristicDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.adventureCharacteristic).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
