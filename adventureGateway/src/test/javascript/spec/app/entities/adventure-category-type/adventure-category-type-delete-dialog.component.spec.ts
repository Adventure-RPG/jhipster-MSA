/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AdventureGatewayTestModule } from '../../../test.module';
import { AdventureCategoryTypeDeleteDialogComponent } from 'app/entities/adventure-category-type/adventure-category-type-delete-dialog.component';
import { AdventureCategoryTypeService } from 'app/entities/adventure-category-type/adventure-category-type.service';

describe('Component Tests', () => {
    describe('AdventureCategoryType Management Delete Component', () => {
        let comp: AdventureCategoryTypeDeleteDialogComponent;
        let fixture: ComponentFixture<AdventureCategoryTypeDeleteDialogComponent>;
        let service: AdventureCategoryTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdventureGatewayTestModule],
                declarations: [AdventureCategoryTypeDeleteDialogComponent]
            })
                .overrideTemplate(AdventureCategoryTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdventureCategoryTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdventureCategoryTypeService);
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
