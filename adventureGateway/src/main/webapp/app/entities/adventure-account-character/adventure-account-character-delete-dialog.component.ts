import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdventureAccountCharacter } from 'app/shared/model/adventure-account-character.model';
import { AdventureAccountCharacterService } from './adventure-account-character.service';

@Component({
    selector: 'jhi-adventure-account-character-delete-dialog',
    templateUrl: './adventure-account-character-delete-dialog.component.html'
})
export class AdventureAccountCharacterDeleteDialogComponent {
    adventureAccountCharacter: IAdventureAccountCharacter;

    constructor(
        protected adventureAccountCharacterService: AdventureAccountCharacterService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.adventureAccountCharacterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'adventureAccountCharacterListModification',
                content: 'Deleted an adventureAccountCharacter'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-adventure-account-character-delete-popup',
    template: ''
})
export class AdventureAccountCharacterDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ adventureAccountCharacter }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AdventureAccountCharacterDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.adventureAccountCharacter = adventureAccountCharacter;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/adventure-account-character', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/adventure-account-character', { outlets: { popup: null } }]);
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
