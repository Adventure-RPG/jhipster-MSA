/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureAccountCharacterDeleteDialogComponent } from 'app/entities/adventure-account-character/adventure-account-character-delete-dialog.component';
import { AdventureAccountCharacterService } from 'app/entities/adventure-account-character/adventure-account-character.service';

describe('Component Tests', () => {
    describe('AdventureAccountCharacter Management Delete Component', () => {
        let comp: AdventureAccountCharacterDeleteDialogComponent;
        let fixture: ComponentFixture<AdventureAccountCharacterDeleteDialogComponent>;
        let service: AdventureAccountCharacterService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureAccountCharacterDeleteDialogComponent]
            })
                .overrideTemplate(AdventureAccountCharacterDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdventureAccountCharacterDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdventureAccountCharacterService);
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
