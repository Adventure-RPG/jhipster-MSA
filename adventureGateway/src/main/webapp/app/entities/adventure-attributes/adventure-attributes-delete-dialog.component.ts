import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdventureAttributes } from 'app/shared/model/adventure-attributes.model';
import { AdventureAttributesService } from './adventure-attributes.service';

@Component({
    selector: 'jhi-adventure-attributes-delete-dialog',
    templateUrl: './adventure-attributes-delete-dialog.component.html'
})
export class AdventureAttributesDeleteDialogComponent {
    adventureAttributes: IAdventureAttributes;

    constructor(
        protected adventureAttributesService: AdventureAttributesService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.adventureAttributesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'adventureAttributesListModification',
                content: 'Deleted an adventureAttributes'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-adventure-attributes-delete-popup',
    template: ''
})
export class AdventureAttributesDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ adventureAttributes }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AdventureAttributesDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.adventureAttributes = adventureAttributes;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/adventure-attributes', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/adventure-attributes', { outlets: { popup: null } }]);
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
