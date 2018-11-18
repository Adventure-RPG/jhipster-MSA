/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureFractionDeleteDialogComponent } from 'app/entities/adventure-fraction/adventure-fraction-delete-dialog.component';
import { AdventureFractionService } from 'app/entities/adventure-fraction/adventure-fraction.service';

describe('Component Tests', () => {
    describe('AdventureFraction Management Delete Component', () => {
        let comp: AdventureFractionDeleteDialogComponent;
        let fixture: ComponentFixture<AdventureFractionDeleteDialogComponent>;
        let service: AdventureFractionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureFractionDeleteDialogComponent]
            })
                .overrideTemplate(AdventureFractionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdventureFractionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdventureFractionService);
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
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
