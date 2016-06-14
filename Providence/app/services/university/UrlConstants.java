package services.university;

public class UrlConstants {

    /**
     * Вся структура университета с факультетами,
     * кафедрами, преподавателями.
     */
    public static final String P_API_PODR_JSON = "http://cist.nure.ua/ias/app/tt/P_API_PODR_JSON";
    /**
     * Вся структура университета с факультетами,
     направлениями, специальностями и группами
     студентов. Направления включают только
     группы бакалавров. Для групп специалистов и
     магистров направления относятся к
     специальностям, которые входят в направления
     */
    public static final String P_API_GROUP_JSON = "http://cist.nure.ua/ias/app/tt/P_API_GROUP_JSON";
    /**
     * Вся структура университета с корпусами,
     аудиториями и видами аудиторий. Аудитории
     расположены в корпусах. Каждая аудитория
     может относиться к нулю, одному или
     нескольким типам.
     */
    public static final String P_API_AUDITORIES_JSON = "http://cist.nure.ua/ias/app/tt/P_API_AUDITORIES_JSON";

    /**
     *
     * Расписание событий (пар), для преподавателя,
     группы или аудитории.
     Пара – это факт встречи во времени и
     пространстве/аудитории заинтересованных
     лиц/групп/преподавателей с определенной
     целью/предметом.
     На одной паре у академической группы может
     быть несколько дисциплин одновременно (наклад-
     ки), это допустимо для альтернативных дисциплин.
     Уникальной является комбинация "время пары +
     дисциплина".

     * @apiNote timetable_id – id группы или
     преподавателя.
     @implSpec
     <i>type_id:</i>: 1 – группа (по умолчанию),
     2 – преподаватель, 3 – аудитория.
     <br>
     <i>time_from</i> – дата начала выборки из
     расписания (по умолчанию – начало
     текущего семестра).
     <br>
     <i>time_to</i> – дата конца выборки из
     расписания (по умолчанию – конец
     текущего семестра).

     */
    public static final String P_API_EVENT_JSON = "http://cist.nure.ua/ias/app/tt/P_API_EVENT_JSON";

    /**
     *
     * Расписание пар только для группы.
     * @apiNote p_id_group – id группы
     @implSpec
     <i>time_from</i> – дата начала выборки из
     расписания (по умолчанию – начало
     текущего семестра).
     <br>
     <i>time_to</i> – дата конца выборки из
     расписания (по умолчанию – конец
     текущего семестра).
     */
    public static final String P_API_EVENTS_GROUP_JSON = "http://cist.nure.ua/ias/app/tt/P_API_EVENTS_GROUP_JSON";

    /**
     *
     * Расписание пар только для преподавателя.
     * @apiNote p_id_teacher – id преподавателя
     @implSpec
     <i>time_from</i> – дата начала выборки из
     расписания (по умолчанию – начало
     текущего семестра).
     <br>
     <i>time_to</i> – дата конца выборки из
     расписания (по умолчанию – конец
     текущего семестра).
     */
    public static final String P_API_EVENTS_TEACHER_JSON = "http://cist.nure.ua/ias/app/tt/P_API_EVENTS_TEACHER_JSON";

    /**
     *
     * Расписание пар только для аудитории.
     * @apiNote p_id_auditory – id аудитории
     @implSpec
     <i>time_from</i> – дата начала выборки из
     расписания (по умолчанию – начало
     текущего семестра).
     <br>
     <i>time_to</i> – дата конца выборки из
     расписания (по умолчанию – конец
     текущего семестра).
     */
    public static final String P_API_EVENTS_AUDITORY_JSON = "http://cist.nure.ua/ias/app/tt/P_API_EVENTS_AUDITORY_JSON";


    /**
     * Все виды аудиторий университета
     */
    public static final String P_API_AUDITORY_TYPES_JSON = "http://cist.nure.ua/ias/app/tt/P_API_PODR_JSON";

}
