/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureModelOptionsUpdateComponent } from 'app/entities/adventure-model-options/adventure-model-options-update.component';
import { AdventureModelOptionsService } from 'app/entities/adventure-model-options/adventure-model-options.service';
import { AdventureModelOptions } from 'app/shared/model/adventure-model-options.model';

describe('Component Tests', () => {
  describe('AdventureModelOptions Management Update Component', () => {
    let comp: AdventureModelOptionsUpdateComponent;
    let fixture: ComponentFixture<AdventureModelOptionsUpdateComponent>;
    let service: AdventureModelOptionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdventureGatewayTestModule],
        declarations: [AdventureModelOptionsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AdventureModelOptionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdventureModelOptionsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdventureModelOptionsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AdventureModelOptions('123');
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
        const entity = new AdventureModelOptions();
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
