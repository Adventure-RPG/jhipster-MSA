/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureModelDeleteDialogComponent } from 'app/entities/adventure-model/adventure-model-delete-dialog.component';
import { AdventureModelService } from 'app/entities/adventure-model/adventure-model.service';

describe('Component Tests', () => {
  describe('AdventureModel Management Delete Component', () => {
    let comp: AdventureModelDeleteDialogComponent;
    let fixture: ComponentFixture<AdventureModelDeleteDialogComponent>;
    let service: AdventureModelService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdventureGatewayTestModule],
        declarations: [AdventureModelDeleteDialogComponent]
      })
        .overrideTemplate(AdventureModelDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AdventureModelDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdventureModelService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete('123');
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith('123');
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
