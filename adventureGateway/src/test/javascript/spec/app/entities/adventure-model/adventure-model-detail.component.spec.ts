/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureModelDetailComponent } from 'app/entities/adventure-model/adventure-model-detail.component';
import { AdventureModel } from 'app/shared/model/adventure-model.model';

describe('Component Tests', () => {
    describe('AdventureModel Management Detail Component', () => {
        let comp: AdventureModelDetailComponent;
        let fixture: ComponentFixture<AdventureModelDetailComponent>;
        const route = ({ data: of({ adventureModel: new AdventureModel('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureModelDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AdventureModelDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdventureModelDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.adventureModel).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
