/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureModelOptionsDetailComponent } from 'app/entities/adventure-model-options/adventure-model-options-detail.component';
import { AdventureModelOptions } from 'app/shared/model/adventure-model-options.model';

describe('Component Tests', () => {
    describe('AdventureModelOptions Management Detail Component', () => {
        let comp: AdventureModelOptionsDetailComponent;
        let fixture: ComponentFixture<AdventureModelOptionsDetailComponent>;
        const route = ({ data: of({ adventureModelOptions: new AdventureModelOptions('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureModelOptionsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AdventureModelOptionsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdventureModelOptionsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.adventureModelOptions).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
