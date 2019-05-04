import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdventureSkill } from 'app/shared/model/adventure-skill.model';
import { AdventureSkillService } from './adventure-skill.service';

@Component({
  selector: 'jhi-adventure-skill-delete-dialog',
  templateUrl: './adventure-skill-delete-dialog.component.html'
})
export class AdventureSkillDeleteDialogComponent {
  adventureSkill: IAdventureSkill;

  constructor(
    protected adventureSkillService: AdventureSkillService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.adventureSkillService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'adventureSkillListModification',
        content: 'Deleted an adventureSkill'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-adventure-skill-delete-popup',
  template: ''
})
export class AdventureSkillDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ adventureSkill }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AdventureSkillDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.adventureSkill = adventureSkill;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/adventure-skill', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/adventure-skill', { outlets: { popup: null } }]);
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
