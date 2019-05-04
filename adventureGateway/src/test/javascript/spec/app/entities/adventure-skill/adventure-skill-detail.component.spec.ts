/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureSkillDetailComponent } from 'app/entities/adventure-skill/adventure-skill-detail.component';
import { AdventureSkill } from 'app/shared/model/adventure-skill.model';

describe('Component Tests', () => {
  describe('AdventureSkill Management Detail Component', () => {
    let comp: AdventureSkillDetailComponent;
    let fixture: ComponentFixture<AdventureSkillDetailComponent>;
    const route = ({ data: of({ adventureSkill: new AdventureSkill('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdventureGatewayTestModule],
        declarations: [AdventureSkillDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AdventureSkillDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AdventureSkillDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.adventureSkill).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
