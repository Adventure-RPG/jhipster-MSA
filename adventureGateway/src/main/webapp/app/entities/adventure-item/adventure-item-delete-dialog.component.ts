import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdventureItem } from 'app/shared/model/adventure-item.model';
import { AdventureItemService } from './adventure-item.service';

@Component({
  selector: 'jhi-adventure-item-delete-dialog',
  templateUrl: './adventure-item-delete-dialog.component.html'
})
export class AdventureItemDeleteDialogComponent {
  adventureItem: IAdventureItem;

  constructor(
    protected adventureItemService: AdventureItemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.adventureItemService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'adventureItemListModification',
        content: 'Deleted an adventureItem'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-adventure-item-delete-popup',
  template: ''
})
export class AdventureItemDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ adventureItem }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AdventureItemDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.adventureItem = adventureItem;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/adventure-item', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/adventure-item', { outlets: { popup: null } }]);
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
