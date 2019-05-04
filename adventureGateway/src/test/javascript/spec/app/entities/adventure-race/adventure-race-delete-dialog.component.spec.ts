/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureRaceDeleteDialogComponent } from 'app/entities/adventure-race/adventure-race-delete-dialog.component';
import { AdventureRaceService } from 'app/entities/adventure-race/adventure-race.service';

describe('Component Tests', () => {
  describe('AdventureRace Management Delete Component', () => {
    let comp: AdventureRaceDeleteDialogComponent;
    let fixture: ComponentFixture<AdventureRaceDeleteDialogComponent>;
    let service: AdventureRaceService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdventureGatewayTestModule],
        declarations: [AdventureRaceDeleteDialogComponent]
      })
        .overrideTemplate(AdventureRaceDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AdventureRaceDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdventureRaceService);
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
