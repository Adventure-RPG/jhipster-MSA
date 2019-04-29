/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureAttributesDetailComponent } from 'app/entities/adventure-attributes/adventure-attributes-detail.component';
import { AdventureAttributes } from 'app/shared/model/adventure-attributes.model';

describe('Component Tests', () => {
    describe('AdventureAttributes Management Detail Component', () => {
        let comp: AdventureAttributesDetailComponent;
        let fixture: ComponentFixture<AdventureAttributesDetailComponent>;
        const route = ({ data: of({ adventureAttributes: new AdventureAttributes('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureAttributesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AdventureAttributesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdventureAttributesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.adventureAttributes).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
