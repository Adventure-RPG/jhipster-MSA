import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdventureCategoryType } from 'app/shared/model/adventure-category-type.model';
import { AdventureCategoryTypeService } from './adventure-category-type.service';

@Component({
  selector: 'jhi-adventure-category-type-delete-dialog',
  templateUrl: './adventure-category-type-delete-dialog.component.html'
})
export class AdventureCategoryTypeDeleteDialogComponent {
  adventureCategoryType: IAdventureCategoryType;

  constructor(
    protected adventureCategoryTypeService: AdventureCategoryTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.adventureCategoryTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'adventureCategoryTypeListModification',
        content: 'Deleted an adventureCategoryType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-adventure-category-type-delete-popup',
  template: ''
})
export class AdventureCategoryTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ adventureCategoryType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AdventureCategoryTypeDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.adventureCategoryType = adventureCategoryType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/adventure-category-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/adventure-category-type', { outlets: { popup: null } }]);
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
