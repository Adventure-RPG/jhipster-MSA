import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAdventureModelOptions, AdventureModelOptions } from 'app/shared/model/adventure-model-options.model';
import { AdventureModelOptionsService } from './adventure-model-options.service';

@Component({
  selector: 'jhi-adventure-model-options-update',
  templateUrl: './adventure-model-options-update.component.html'
})
export class AdventureModelOptionsUpdateComponent implements OnInit {
  adventureModelOptions: IAdventureModelOptions;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    color: []
  });

  constructor(
    protected adventureModelOptionsService: AdventureModelOptionsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ adventureModelOptions }) => {
      this.updateForm(adventureModelOptions);
      this.adventureModelOptions = adventureModelOptions;
    });
  }

  updateForm(adventureModelOptions: IAdventureModelOptions) {
    this.editForm.patchValue({
      id: adventureModelOptions.id,
      color: adventureModelOptions.color
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const adventureModelOptions = this.createFromForm();
    if (adventureModelOptions.id !== undefined) {
      this.subscribeToSaveResponse(this.adventureModelOptionsService.update(adventureModelOptions));
    } else {
      this.subscribeToSaveResponse(this.adventureModelOptionsService.create(adventureModelOptions));
    }
  }

  private createFromForm(): IAdventureModelOptions {
    const entity = {
      ...new AdventureModelOptions(),
      id: this.editForm.get(['id']).value,
      color: this.editForm.get(['color']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureModelOptions>>) {
    result.subscribe((res: HttpResponse<IAdventureModelOptions>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
