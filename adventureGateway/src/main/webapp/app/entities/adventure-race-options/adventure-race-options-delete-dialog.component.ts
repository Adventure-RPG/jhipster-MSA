import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdventureRaceOptions } from 'app/shared/model/adventure-race-options.model';
import { AdventureRaceOptionsService } from './adventure-race-options.service';

@Component({
    selector: 'jhi-adventure-race-options-delete-dialog',
    templateUrl: './adventure-race-options-delete-dialog.component.html'
})
export class AdventureRaceOptionsDeleteDialogComponent {
    adventureRaceOptions: IAdventureRaceOptions;

    constructor(
        protected adventureRaceOptionsService: AdventureRaceOptionsService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.adventureRaceOptionsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'adventureRaceOptionsListModification',
                content: 'Deleted an adventureRaceOptions'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-adventure-race-options-delete-popup',
    template: ''
})
export class AdventureRaceOptionsDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ adventureRaceOptions }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AdventureRaceOptionsDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.adventureRaceOptions = adventureRaceOptions;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/adventure-race-options', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/adventure-race-options', { outlets: { popup: null } }]);
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
