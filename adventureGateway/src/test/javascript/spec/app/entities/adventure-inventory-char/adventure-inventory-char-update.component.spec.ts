/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureInventoryCharUpdateComponent } from 'app/entities/adventure-inventory-char/adventure-inventory-char-update.component';
import { AdventureInventoryCharService } from 'app/entities/adventure-inventory-char/adventure-inventory-char.service';
import { AdventureInventoryChar } from 'app/shared/model/adventure-inventory-char.model';

describe('Component Tests', () => {
  describe('AdventureInventoryChar Management Update Component', () => {
    let comp: AdventureInventoryCharUpdateComponent;
    let fixture: ComponentFixture<AdventureInventoryCharUpdateComponent>;
    let service: AdventureInventoryCharService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdventureGatewayTestModule],
        declarations: [AdventureInventoryCharUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AdventureInventoryCharUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdventureInventoryCharUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdventureInventoryCharService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AdventureInventoryChar('123');
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
        const entity = new AdventureInventoryChar();
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
