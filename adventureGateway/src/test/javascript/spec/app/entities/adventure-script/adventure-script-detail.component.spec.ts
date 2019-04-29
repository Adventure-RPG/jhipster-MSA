/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureScriptDetailComponent } from 'app/entities/adventure-script/adventure-script-detail.component';
import { AdventureScript } from 'app/shared/model/adventure-script.model';

describe('Component Tests', () => {
    describe('AdventureScript Management Detail Component', () => {
        let comp: AdventureScriptDetailComponent;
        let fixture: ComponentFixture<AdventureScriptDetailComponent>;
        const route = ({ data: of({ adventureScript: new AdventureScript('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureScriptDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AdventureScriptDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdventureScriptDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.adventureScript).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
