/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureItemUpdateComponent } from 'app/entities/adventure-item/adventure-item-update.component';
import { AdventureItemService } from 'app/entities/adventure-item/adventure-item.service';
import { AdventureItem } from 'app/shared/model/adventure-item.model';

describe('Component Tests', () => {
  describe('AdventureItem Management Update Component', () => {
    let comp: AdventureItemUpdateComponent;
    let fixture: ComponentFixture<AdventureItemUpdateComponent>;
    let service: AdventureItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdventureGatewayTestModule],
        declarations: [AdventureItemUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AdventureItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdventureItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdventureItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AdventureItem('123');
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
        const entity = new AdventureItem();
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
