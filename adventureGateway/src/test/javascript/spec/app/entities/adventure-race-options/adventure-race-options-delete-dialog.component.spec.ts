/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureRaceOptionsDeleteDialogComponent } from 'app/entities/adventure-race-options/adventure-race-options-delete-dialog.component';
import { AdventureRaceOptionsService } from 'app/entities/adventure-race-options/adventure-race-options.service';

describe('Component Tests', () => {
  describe('AdventureRaceOptions Management Delete Component', () => {
    let comp: AdventureRaceOptionsDeleteDialogComponent;
    let fixture: ComponentFixture<AdventureRaceOptionsDeleteDialogComponent>;
    let service: AdventureRaceOptionsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdventureGatewayTestModule],
        declarations: [AdventureRaceOptionsDeleteDialogComponent]
      })
        .overrideTemplate(AdventureRaceOptionsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AdventureRaceOptionsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdventureRaceOptionsService);
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
