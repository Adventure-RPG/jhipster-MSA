import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdventureCharacteristic } from 'app/shared/model/adventure-characteristic.model';
import { AdventureCharacteristicService } from './adventure-characteristic.service';

@Component({
    selector: 'jhi-adventure-characteristic-delete-dialog',
    templateUrl: './adventure-characteristic-delete-dialog.component.html'
})
export class AdventureCharacteristicDeleteDialogComponent {
    adventureCharacteristic: IAdventureCharacteristic;

    constructor(
        protected adventureCharacteristicService: AdventureCharacteristicService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.adventureCharacteristicService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'adventureCharacteristicListModification',
                content: 'Deleted an adventureCharacteristic'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-adventure-characteristic-delete-popup',
    template: ''
})
export class AdventureCharacteristicDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ adventureCharacteristic }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AdventureCharacteristicDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.adventureCharacteristic = adventureCharacteristic;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/adventure-characteristic', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/adventure-characteristic', { outlets: { popup: null } }]);
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
