import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAdventureInventoryChar, AdventureInventoryChar } from 'app/shared/model/adventure-inventory-char.model';
import { AdventureInventoryCharService } from './adventure-inventory-char.service';
import { IAdventureItem } from 'app/shared/model/adventure-item.model';
import { AdventureItemService } from 'app/entities/adventure-item';

@Component({
  selector: 'jhi-adventure-inventory-char-update',
  templateUrl: './adventure-inventory-char-update.component.html'
})
export class AdventureInventoryCharUpdateComponent implements OnInit {
  adventureInventoryChar: IAdventureInventoryChar;
  isSaving: boolean;

  adventureitems: IAdventureItem[];

  editForm = this.fb.group({
    id: [],
    adventureItemId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected adventureInventoryCharService: AdventureInventoryCharService,
    protected adventureItemService: AdventureItemService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ adventureInventoryChar }) => {
      this.updateForm(adventureInventoryChar);
      this.adventureInventoryChar = adventureInventoryChar;
    });
    this.adventureItemService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAdventureItem[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAdventureItem[]>) => response.body)
      )
      .subscribe((res: IAdventureItem[]) => (this.adventureitems = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(adventureInventoryChar: IAdventureInventoryChar) {
    this.editForm.patchValue({
      id: adventureInventoryChar.id,
      adventureItemId: adventureInventoryChar.adventureItemId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const adventureInventoryChar = this.createFromForm();
    if (adventureInventoryChar.id !== undefined) {
      this.subscribeToSaveResponse(this.adventureInventoryCharService.update(adventureInventoryChar));
    } else {
      this.subscribeToSaveResponse(this.adventureInventoryCharService.create(adventureInventoryChar));
    }
  }

  private createFromForm(): IAdventureInventoryChar {
    const entity = {
      ...new AdventureInventoryChar(),
      id: this.editForm.get(['id']).value,
      adventureItemId: this.editForm.get(['adventureItemId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureInventoryChar>>) {
    result.subscribe((res: HttpResponse<IAdventureInventoryChar>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackAdventureItemById(index: number, item: IAdventureItem) {
    return item.id;
  }
}
