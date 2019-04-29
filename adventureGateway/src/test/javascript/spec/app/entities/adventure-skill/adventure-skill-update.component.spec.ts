/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureSkillUpdateComponent } from 'app/entities/adventure-skill/adventure-skill-update.component';
import { AdventureSkillService } from 'app/entities/adventure-skill/adventure-skill.service';
import { AdventureSkill } from 'app/shared/model/adventure-skill.model';

describe('Component Tests', () => {
    describe('AdventureSkill Management Update Component', () => {
        let comp: AdventureSkillUpdateComponent;
        let fixture: ComponentFixture<AdventureSkillUpdateComponent>;
        let service: AdventureSkillService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureSkillUpdateComponent]
            })
                .overrideTemplate(AdventureSkillUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AdventureSkillUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdventureSkillService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AdventureSkill('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.adventureSkill = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AdventureSkill();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.adventureSkill = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
