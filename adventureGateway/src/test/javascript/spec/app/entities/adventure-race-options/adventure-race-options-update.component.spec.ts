/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureRaceOptionsUpdateComponent } from 'app/entities/adventure-race-options/adventure-race-options-update.component';
import { AdventureRaceOptionsService } from 'app/entities/adventure-race-options/adventure-race-options.service';
import { AdventureRaceOptions } from 'app/shared/model/adventure-race-options.model';

describe('Component Tests', () => {
  describe('AdventureRaceOptions Management Update Component', () => {
    let comp: AdventureRaceOptionsUpdateComponent;
    let fixture: ComponentFixture<AdventureRaceOptionsUpdateComponent>;
    let service: AdventureRaceOptionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdventureGatewayTestModule],
        declarations: [AdventureRaceOptionsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AdventureRaceOptionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdventureRaceOptionsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdventureRaceOptionsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AdventureRaceOptions('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AdventureRaceOptions();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
