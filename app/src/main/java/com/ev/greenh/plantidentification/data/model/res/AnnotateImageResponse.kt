package com.ev.greenh.plantidentification.data.model.res

/*
 * Created by Sudhanshu Kumar on 10/10/23.
 */

data class AnnotateImageResponse(
    val faceAnnotations: List<FaceAnnotation>,
    val landmarkAnnotations: List<EntityAnnotation>,
    val logoAnnotations: List<EntityAnnotation>,
    val labelAnnotations: List<EntityAnnotation>,
    val localizedObjectAnnotations: List<LocalizedObjectAnnotation>,
    val textAnnotations: List<EntityAnnotation>,
    val fullTextAnnotation: TextAnnotation?,
    val safeSearchAnnotation: SafeSearchAnnotation?,
    val imagePropertiesAnnotation: ImageProperties?,
    val cropHintsAnnotation: CropHintsAnnotation?,
    val webDetection: WebDetection?,
    val productSearchResults: ProductSearchResults?,
    val error: Status?,
    val context: ImageAnnotationContext?
)