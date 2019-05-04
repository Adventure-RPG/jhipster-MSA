/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureAccountCharacterUpdateComponent } from 'app/entities/adventure-account-character/adventure-account-character-update.component';
import { AdventureAccountCharacterService } from 'app/entities/adventure-account-character/adventure-account-character.service';
import { AdventureAccountCharacter } from 'app/shared/model/adventure-account-character.model';

describe('Component Tests', () => {
  describe('AdventureAccountCharacter Management Update Component', () => {
    let comp: AdventureAccountCharacterUpdateComponent;
    let fixture: ComponentFixture<AdventureAccountCharacterUpdateComponent>;
    let service: AdventureAccountCharacterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdventureGatewayTestModule],
        declarations: [AdventureAccountCharacterUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AdventureAccountCharacterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdventureAccountCharacterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdventureAccountCharacterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AdventureAccountCharacter('123');
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
        const entity = new AdventureAccountCharacter();
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
