/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureInventoryCharDetailComponent } from 'app/entities/adventure-inventory-char/adventure-inventory-char-detail.component';
import { AdventureInventoryChar } from 'app/shared/model/adventure-inventory-char.model';

describe('Component Tests', () => {
    describe('AdventureInventoryChar Management Detail Component', () => {
        let comp: AdventureInventoryCharDetailComponent;
        let fixture: ComponentFixture<AdventureInventoryCharDetailComponent>;
        const route = ({ data: of({ adventureInventoryChar: new AdventureInventoryChar('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureInventoryCharDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AdventureInventoryCharDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdventureInventoryCharDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.adventureInventoryChar).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
