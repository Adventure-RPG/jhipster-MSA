import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAdventureItem, AdventureItem } from 'app/shared/model/adventure-item.model';
import { AdventureItemService } from './adventure-item.service';
import { IAdventureAttributes } from 'app/shared/model/adventure-attributes.model';
import { AdventureAttributesService } from 'app/entities/adventure-attributes';

@Component({
  selector: 'jhi-adventure-item-update',
  templateUrl: './adventure-item-update.component.html'
})
export class AdventureItemUpdateComponent implements OnInit {
  adventureItem: IAdventureItem;
  isSaving: boolean;

  adventureattributes: IAdventureAttributes[];

  editForm = this.fb.group({
    id: [],
    isEquipment: [],
    equipmentSlot: [],
    price: [],
    weight: [],
    adventureAttributesId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected adventureItemService: AdventureItemService,
    protected adventureAttributesService: AdventureAttributesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ adventureItem }) => {
      this.updateForm(adventureItem);
      this.adventureItem = adventureItem;
    });
    this.adventureAttributesService
      .query({ filter: 'adventureitem-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IAdventureAttributes[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAdventureAttributes[]>) => response.body)
      )
      .subscribe(
        (res: IAdventureAttributes[]) => {
          if (!this.adventureItem.adventureAttributesId) {
            this.adventureattributes = res;
          } else {
            this.adventureAttributesService
              .find(this.adventureItem.adventureAttributesId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IAdventureAttributes>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IAdventureAttributes>) => subResponse.body)
              )
              .subscribe(
                (subRes: IAdventureAttributes) => (this.adventureattributes = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(adventureItem: IAdventureItem) {
    this.editForm.patchValue({
      id: adventureItem.id,
      isEquipment: adventureItem.isEquipment,
      equipmentSlot: adventureItem.equipmentSlot,
      price: adventureItem.price,
      weight: adventureItem.weight,
      adventureAttributesId: adventureItem.adventureAttributesId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const adventureItem = this.createFromForm();
    if (adventureItem.id !== undefined) {
      this.subscribeToSaveResponse(this.adventureItemService.update(adventureItem));
    } else {
      this.subscribeToSaveResponse(this.adventureItemService.create(adventureItem));
    }
  }

  private createFromForm(): IAdventureItem {
    const entity = {
      ...new AdventureItem(),
      id: this.editForm.get(['id']).value,
      isEquipment: this.editForm.get(['isEquipment']).value,
      equipmentSlot: this.editForm.get(['equipmentSlot']).value,
      price: this.editForm.get(['price']).value,
      weight: this.editForm.get(['weight']).value,
      adventureAttributesId: this.editForm.get(['adventureAttributesId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureItem>>) {
    result.subscribe((res: HttpResponse<IAdventureItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackAdventureAttributesById(index: number, item: IAdventureAttributes) {
    return item.id;
  }
}
