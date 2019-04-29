/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureAttributesDeleteDialogComponent } from 'app/entities/adventure-attributes/adventure-attributes-delete-dialog.component';
import { AdventureAttributesService } from 'app/entities/adventure-attributes/adventure-attributes.service';

describe('Component Tests', () => {
    describe('AdventureAttributes Management Delete Component', () => {
        let comp: AdventureAttributesDeleteDialogComponent;
        let fixture: ComponentFixture<AdventureAttributesDeleteDialogComponent>;
        let service: AdventureAttributesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureAttributesDeleteDialogComponent]
            })
                .overrideTemplate(AdventureAttributesDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdventureAttributesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdventureAttributesService);
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
