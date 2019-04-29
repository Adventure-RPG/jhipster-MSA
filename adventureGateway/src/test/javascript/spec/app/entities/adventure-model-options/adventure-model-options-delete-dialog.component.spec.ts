/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureModelOptionsDeleteDialogComponent } from 'app/entities/adventure-model-options/adventure-model-options-delete-dialog.component';
import { AdventureModelOptionsService } from 'app/entities/adventure-model-options/adventure-model-options.service';

describe('Component Tests', () => {
    describe('AdventureModelOptions Management Delete Component', () => {
        let comp: AdventureModelOptionsDeleteDialogComponent;
        let fixture: ComponentFixture<AdventureModelOptionsDeleteDialogComponent>;
        let service: AdventureModelOptionsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureModelOptionsDeleteDialogComponent]
            })
                .overrideTemplate(AdventureModelOptionsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdventureModelOptionsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdventureModelOptionsService);
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
