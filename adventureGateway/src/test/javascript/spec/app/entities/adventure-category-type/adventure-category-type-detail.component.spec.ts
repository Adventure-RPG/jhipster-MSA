/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureCategoryTypeDetailComponent } from 'app/entities/adventure-category-type/adventure-category-type-detail.component';
import { AdventureCategoryType } from 'app/shared/model/adventure-category-type.model';

describe('Component Tests', () => {
    describe('AdventureCategoryType Management Detail Component', () => {
        let comp: AdventureCategoryTypeDetailComponent;
        let fixture: ComponentFixture<AdventureCategoryTypeDetailComponent>;
        const route = ({ data: of({ adventureCategoryType: new AdventureCategoryType('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureCategoryTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AdventureCategoryTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdventureCategoryTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.adventureCategoryType).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
