import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdventureInventoryChar } from 'app/shared/model/adventure-inventory-char.model';
import { AdventureInventoryCharService } from './adventure-inventory-char.service';

@Component({
    selector: 'jhi-adventure-inventory-char-delete-dialog',
    templateUrl: './adventure-inventory-char-delete-dialog.component.html'
})
export class AdventureInventoryCharDeleteDialogComponent {
    adventureInventoryChar: IAdventureInventoryChar;

    constructor(
        protected adventureInventoryCharService: AdventureInventoryCharService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.adventureInventoryCharService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'adventureInventoryCharListModification',
                content: 'Deleted an adventureInventoryChar'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-adventure-inventory-char-delete-popup',
    template: ''
})
export class AdventureInventoryCharDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ adventureInventoryChar }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AdventureInventoryCharDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.adventureInventoryChar = adventureInventoryChar;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/adventure-inventory-char', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/adventure-inventory-char', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
