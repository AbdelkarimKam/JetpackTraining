package com.example.jetpacktrainning.tools

interface EntityMapper<Entity, DomaineModel> {
    fun mapFromEntity(entity: Entity): DomaineModel
    fun mapToEntity(domaineModel: DomaineModel): Entity
}