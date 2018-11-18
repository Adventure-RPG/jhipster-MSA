/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureItemDetailComponent } from 'app/entities/adventure-item/adventure-item-detail.component';
import { AdventureItem } from 'app/shared/model/adventure-item.model';

describe('Component Tests', () => {
    describe('AdventureItem Management Detail Component', () => {
        let comp: AdventureItemDetailComponent;
        let fixture: ComponentFixture<AdventureItemDetailComponent>;
        const route = ({ data: of({ adventureItem: new AdventureItem(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureItemDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AdventureItemDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdventureItemDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.adventureItem).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
