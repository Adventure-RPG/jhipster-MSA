/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureAccountCharacterDetailComponent } from 'app/entities/adventure-account-character/adventure-account-character-detail.component';
import { AdventureAccountCharacter } from 'app/shared/model/adventure-account-character.model';

describe('Component Tests', () => {
    describe('AdventureAccountCharacter Management Detail Component', () => {
        let comp: AdventureAccountCharacterDetailComponent;
        let fixture: ComponentFixture<AdventureAccountCharacterDetailComponent>;
        const route = ({ data: of({ adventureAccountCharacter: new AdventureAccountCharacter(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureAccountCharacterDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AdventureAccountCharacterDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdventureAccountCharacterDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.adventureAccountCharacter).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
