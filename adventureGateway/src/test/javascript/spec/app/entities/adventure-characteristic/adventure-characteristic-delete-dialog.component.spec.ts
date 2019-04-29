/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureCharacteristicDeleteDialogComponent } from 'app/entities/adventure-characteristic/adventure-characteristic-delete-dialog.component';
import { AdventureCharacteristicService } from 'app/entities/adventure-characteristic/adventure-characteristic.service';

describe('Component Tests', () => {
    describe('AdventureCharacteristic Management Delete Component', () => {
        let comp: AdventureCharacteristicDeleteDialogComponent;
        let fixture: ComponentFixture<AdventureCharacteristicDeleteDialogComponent>;
        let service: AdventureCharacteristicService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureCharacteristicDeleteDialogComponent]
            })
                .overrideTemplate(AdventureCharacteristicDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdventureCharacteristicDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdventureCharacteristicService);
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
