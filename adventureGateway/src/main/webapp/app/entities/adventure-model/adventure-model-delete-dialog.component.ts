import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdventureModel } from 'app/shared/model/adventure-model.model';
import { AdventureModelService } from './adventure-model.service';

@Component({
    selector: 'jhi-adventure-model-delete-dialog',
    templateUrl: './adventure-model-delete-dialog.component.html'
})
export class AdventureModelDeleteDialogComponent {
    adventureModel: IAdventureModel;

    constructor(
        protected adventureModelService: AdventureModelService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.adventureModelService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'adventureModelListModification',
                content: 'Deleted an adventureModel'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-adventure-model-delete-popup',
    template: ''
})
export class AdventureModelDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ adventureModel }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AdventureModelDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.adventureModel = adventureModel;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/adventure-model', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/adventure-model', { outlets: { popup: null } }]);
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
