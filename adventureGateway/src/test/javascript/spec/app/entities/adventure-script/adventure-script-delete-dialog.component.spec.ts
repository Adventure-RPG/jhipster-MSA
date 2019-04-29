/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureScriptDeleteDialogComponent } from 'app/entities/adventure-script/adventure-script-delete-dialog.component';
import { AdventureScriptService } from 'app/entities/adventure-script/adventure-script.service';

describe('Component Tests', () => {
    describe('AdventureScript Management Delete Component', () => {
        let comp: AdventureScriptDeleteDialogComponent;
        let fixture: ComponentFixture<AdventureScriptDeleteDialogComponent>;
        let service: AdventureScriptService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureScriptDeleteDialogComponent]
            })
                .overrideTemplate(AdventureScriptDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdventureScriptDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdventureScriptService);
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
