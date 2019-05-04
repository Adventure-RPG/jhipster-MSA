import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAdventureCategoryType, AdventureCategoryType } from 'app/shared/model/adventure-category-type.model';
import { AdventureCategoryTypeService } from './adventure-category-type.service';

@Component({
  selector: 'jhi-adventure-category-type-update',
  templateUrl: './adventure-category-type-update.component.html'
})
export class AdventureCategoryTypeUpdateComponent implements OnInit {
  adventureCategoryType: IAdventureCategoryType;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    desc: []
  });

  constructor(
    protected adventureCategoryTypeService: AdventureCategoryTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ adventureCategoryType }) => {
      this.updateForm(adventureCategoryType);
      this.adventureCategoryType = adventureCategoryType;
    });
  }

  updateForm(adventureCategoryType: IAdventureCategoryType) {
    this.editForm.patchValue({
      id: adventureCategoryType.id,
      name: adventureCategoryType.name,
      desc: adventureCategoryType.desc
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const adventureCategoryType = this.createFromForm();
    if (adventureCategoryType.id !== undefined) {
      this.subscribeToSaveResponse(this.adventureCategoryTypeService.update(adventureCategoryType));
    } else {
      this.subscribeToSaveResponse(this.adventureCategoryTypeService.create(adventureCategoryType));
    }
  }

  private createFromForm(): IAdventureCategoryType {
    const entity = {
      ...new AdventureCategoryType(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      desc: this.editForm.get(['desc']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureCategoryType>>) {
    result.subscribe((res: HttpResponse<IAdventureCategoryType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
