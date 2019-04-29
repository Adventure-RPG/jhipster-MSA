import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdventureRace } from 'app/shared/model/adventure-race.model';
import { AdventureRaceService } from './adventure-race.service';

@Component({
    selector: 'jhi-adventure-race-delete-dialog',
    templateUrl: './adventure-race-delete-dialog.component.html'
})
export class AdventureRaceDeleteDialogComponent {
    adventureRace: IAdventureRace;

    constructor(
        protected adventureRaceService: AdventureRaceService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.adventureRaceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'adventureRaceListModification',
                content: 'Deleted an adventureRace'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-adventure-race-delete-popup',
    template: ''
})
export class AdventureRaceDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ adventureRace }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AdventureRaceDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.adventureRace = adventureRace;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/adventure-race', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/adventure-race', { outlets: { popup: null } }]);
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
