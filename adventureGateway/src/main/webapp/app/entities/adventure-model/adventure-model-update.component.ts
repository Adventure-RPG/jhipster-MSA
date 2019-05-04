import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAdventureModel, AdventureModel } from 'app/shared/model/adventure-model.model';
import { AdventureModelService } from './adventure-model.service';
import { IAdventureCategoryType } from 'app/shared/model/adventure-category-type.model';
import { AdventureCategoryTypeService } from 'app/entities/adventure-category-type';
import { IAdventureRaceOptions } from 'app/shared/model/adventure-race-options.model';
import { AdventureRaceOptionsService } from 'app/entities/adventure-race-options';

@Component({
  selector: 'jhi-adventure-model-update',
  templateUrl: './adventure-model-update.component.html'
})
export class AdventureModelUpdateComponent implements OnInit {
  adventureModel: IAdventureModel;
  isSaving: boolean;

  adventurecategorytypes: IAdventureCategoryType[];

  adventureraceoptions: IAdventureRaceOptions[];

  editForm = this.fb.group({
    id: [],
    name: [],
    file: [],
    fileContentType: [],
    adventureCategoryTypeId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected adventureModelService: AdventureModelService,
    protected adventureCategoryTypeService: AdventureCategoryTypeService,
    protected adventureRaceOptionsService: AdventureRaceOptionsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ adventureModel }) => {
      this.updateForm(adventureModel);
      this.adventureModel = adventureModel;
    });
    this.adventureCategoryTypeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAdventureCategoryType[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAdventureCategoryType[]>) => response.body)
      )
      .subscribe(
        (res: IAdventureCategoryType[]) => (this.adventurecategorytypes = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.adventureRaceOptionsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAdventureRaceOptions[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAdventureRaceOptions[]>) => response.body)
      )
      .subscribe(
        (res: IAdventureRaceOptions[]) => (this.adventureraceoptions = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(adventureModel: IAdventureModel) {
    this.editForm.patchValue({
      id: adventureModel.id,
      name: adventureModel.name,
      file: adventureModel.file,
      fileContentType: adventureModel.fileContentType,
      adventureCategoryTypeId: adventureModel.adventureCategoryTypeId
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const adventureModel = this.createFromForm();
    if (adventureModel.id !== undefined) {
      this.subscribeToSaveResponse(this.adventureModelService.update(adventureModel));
    } else {
      this.subscribeToSaveResponse(this.adventureModelService.create(adventureModel));
    }
  }

  private createFromForm(): IAdventureModel {
    const entity = {
      ...new AdventureModel(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      fileContentType: this.editForm.get(['fileContentType']).value,
      file: this.editForm.get(['file']).value,
      adventureCategoryTypeId: this.editForm.get(['adventureCategoryTypeId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureModel>>) {
    result.subscribe((res: HttpResponse<IAdventureModel>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackAdventureCategoryTypeById(index: number, item: IAdventureCategoryType) {
    return item.id;
  }

  trackAdventureRaceOptionsById(index: number, item: IAdventureRaceOptions) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
