import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdventureModelOptions } from 'app/shared/model/adventure-model-options.model';
import { AdventureModelOptionsService } from './adventure-model-options.service';

@Component({
    selector: 'jhi-adventure-model-options-delete-dialog',
    templateUrl: './adventure-model-options-delete-dialog.component.html'
})
export class AdventureModelOptionsDeleteDialogComponent {
    adventureModelOptions: IAdventureModelOptions;

    constructor(
        protected adventureModelOptionsService: AdventureModelOptionsService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.adventureModelOptionsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'adventureModelOptionsListModification',
                content: 'Deleted an adventureModelOptions'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-adventure-model-options-delete-popup',
    template: ''
})
export class AdventureModelOptionsDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ adventureModelOptions }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AdventureModelOptionsDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.adventureModelOptions = adventureModelOptions;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/adventure-model-options', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/adventure-model-options', { outlets: { popup: null } }]);
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
