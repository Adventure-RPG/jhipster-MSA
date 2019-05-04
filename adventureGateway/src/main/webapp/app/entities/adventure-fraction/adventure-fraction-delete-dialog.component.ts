import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdventureFraction } from 'app/shared/model/adventure-fraction.model';
import { AdventureFractionService } from './adventure-fraction.service';

@Component({
  selector: 'jhi-adventure-fraction-delete-dialog',
  templateUrl: './adventure-fraction-delete-dialog.component.html'
})
export class AdventureFractionDeleteDialogComponent {
  adventureFraction: IAdventureFraction;

  constructor(
    protected adventureFractionService: AdventureFractionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.adventureFractionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'adventureFractionListModification',
        content: 'Deleted an adventureFraction'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-adventure-fraction-delete-popup',
  template: ''
})
export class AdventureFractionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ adventureFraction }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AdventureFractionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.adventureFraction = adventureFraction;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/adventure-fraction', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/adventure-fraction', { outlets: { popup: null } }]);
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
