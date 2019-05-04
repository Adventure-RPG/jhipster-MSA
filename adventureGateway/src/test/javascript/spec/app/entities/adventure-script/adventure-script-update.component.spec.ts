/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureScriptUpdateComponent } from 'app/entities/adventure-script/adventure-script-update.component';
import { AdventureScriptService } from 'app/entities/adventure-script/adventure-script.service';
import { AdventureScript } from 'app/shared/model/adventure-script.model';

describe('Component Tests', () => {
  describe('AdventureScript Management Update Component', () => {
    let comp: AdventureScriptUpdateComponent;
    let fixture: ComponentFixture<AdventureScriptUpdateComponent>;
    let service: AdventureScriptService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdventureGatewayTestModule],
        declarations: [AdventureScriptUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AdventureScriptUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdventureScriptUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdventureScriptService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AdventureScript('123');
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
        const entity = new AdventureScript();
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
